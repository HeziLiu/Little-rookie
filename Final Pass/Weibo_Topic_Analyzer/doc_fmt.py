import jieba
import re
import pymongo
import datetime
import time
import pandas as pd
import math
from tools.langconv import *
# 加载停用词
stopwords = {}.fromkeys([line.rstrip() for line in open('Stopword.txt', encoding='utf-8')])
alpha = 0.8
t_now = "2016-10-01 23:59:59"
t_now = time.strptime(t_now, "%Y-%m-%d %H:%M:%S")
t_now = datetime.datetime(t_now[0], t_now[1], t_now[2])

# 格式化tweets文档
def doc_filter():
    myclient = pymongo.MongoClient('localhost', 27017)
    tweets = myclient["weibo"]["tweets"]
    information = myclient["weibo"]["information"]
    count = 0

    # 1.消除微博数少于150的用户及其微博,count为最终删除文档数
    ret = information.delete_many({"Num_Tweets": {"$lt": 150}})
    count += ret.deleted_count
    df = pd.DataFrame(list(information.find({"Num_Tweets": {"$lt": 150}}, {"_id": 1})))
    for i in range(0, 18105):
        j = df.iloc[i, 0]
        x = tweets.delete_many({"ID": j})
        count += x.deleted_count
        print("当前", x.deleted_count, "个文档已删除")

    # 2.删除微博文本字段小于8的
    ret = tweets.delete_many({"Content": {"$regex": "^.{0,8}$"}})
    count += ret.deleted_count

    # 3. 删除Content为空的记录
    ret = tweets.delete_many({"Content": {"$exists": "false"}})
    count += ret.deleted_count

    print(count, "个文档已删除")


# 根据[转发数][评论数][点赞数]→[微博质量]Qm
def get_qm(x):
    qm = x["Transfer"] / (max(x["Transfer"], x["Like"], x["Comment"]) + 1)
    return qm


# 根据[当前日期][发布日期][存活时间T=16.8]→[微博新鲜度]Fm
def get_fm(x):
    # format time
    t = x["PubTime"]
    if "今天" or "分钟" in t:
        t = "2016-10-01"
    elif "月" in t:
        print(1)
        t = "2016-" + t.replace("月", "-").replace("日", '')
    x["PubTime"] = t
    # get_fm
    t = time.strptime(t, '%Y-%m-%d')
    t = datetime.datetime(t[0], t[1], t[2])
    fm = 1 / (math.ceil((t_now - t).days / 16.8) + 1)
    return fm


# 根据Qm和Fm计算Im
def get_im(x, alpha):
    im = x["Qm"] * alpha + (1 - alpha) * x["Fm"]
    return im


# 繁体转简体
def cht_2_chs(x):
    line = Converter('zh-hans').convert(x["Content"])
    line.encode('utf-8')
    return line


def get_data():
    print("连接mysql数据库...")

    db = pymongo.MongoClient('localhost', 27017)

    tweets = db["weibo"]["tweets"]

    tweets = pd.DataFrame(list(tweets.find()))
    # 筛选出Im>0.5的文本
    fm = tweets.apply(lambda x: get_fm(x), axis=1)
    tweets["Fm"] = fm
    qm = tweets.apply(lambda x: get_qm(x), axis=1)
    tweets["Qm"] = qm
    im = tweets.apply(lambda x: get_im(x, alpha), axis=1)
    tweets["Im"] = im
    tweets = tweets[tweets["Im"] > 0.5]
    tweets["Content"].to_csv('data/raw_tweets.txt', index=False, sep="\n")
    print(tweets[tweets["Im"] > 0.5])
    # return tweets


def word_fliter(x):

    x = re.sub(r'[A-Za-z]+|[0-9]+|'                                  # 字母或数字
                              r'@[\u4e00-\u9fa5a-zA-Z0-9_-]{2,30}|'  # @用户名
                              r'[\uAC00-\uD7A3]|'                    # 韩文
                              r'[\u0800-\u4e00]|'                    # 日文
                              r'\[[\u4e00-\u9fa5A-Za-z]{1,7}\]|'
                              r'[\uD800-\uDBFF][\uDC00-\uDFFF]', "", x)  # 微博表情
    ret_str = ''
    for word in x:
        if word not in stopwords:
            ret_str += word
    return ret_str


def word_cut(x):
    return " ".join(jieba.cut(x))


if __name__ == '__main__':
    raw = pd.read_table("./data/raw_tweets.txt", sep="\n", names=["raw_content"])
    raw_f = raw.raw_content.apply(lambda x:word_fliter(x))
    raw['raw_content'] = raw_f
    raw = raw[raw['raw_content'].str.len()>8]
    raw['fmt_content'] = raw.raw_content.apply(word_cut)
    raw["fmt_content"].to_csv('data/fmt_tweets.txt', index=False, sep="\n")
