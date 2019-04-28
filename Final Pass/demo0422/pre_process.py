import jieba
import pymysql
import re
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import KMeans,MiniBatchKMeans
import matplotlib.pyplot as plt
# 加载停用词
stopwords = {}.fromkeys([line.rstrip() for line in open('Stopword.txt', encoding='utf-8')])

def word_cut(text):
    retStr=''
    text = re.sub(r'[A-Za-z]+|@[\u4e00-\u9fa5a-zA-Z0-9_-]{2,30}|[0-9]+','',text)
    for word in text:
        if word not in stopwords:
            retStr += word
    return  " ".join(jieba.cut(retStr))

# 处理微博内容数据
def get_data():
    print("连接mysql数据库...")

    db = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='962464', db='sina_weibo')
    # pandas提取数据库数据
    weibo_content_df = pd.read_sql("select content from weiboblog where content <> '' limit 100",con=db)

    print("============================未处理的文本================================")

    print(weibo_content_df)

    print("======================开始去除用户名,英文，数字==========================")
    #weibo_content_df = weibo_content_df.map(lambda x: x.replace)

    #weibo_content_df['content'].apply(lambda row:row.replace(r'[A-Za-z]+|@[\u4e00-\u9fa5a-zA-Z0-9_-]{2,30}|[0-9]+',''))

    #weibo_content_df['content'].apply(lambda x: ' '.join([word for word in x if word not in stopwords]))

    weibo_content_df['content_cutted'] = weibo_content_df.content.apply(word_cut)
    print("======================处理后的数据======================")
    print(weibo_content_df['content_cutted'])
    # result = list()
    #
    # for i in comment_contents:
    #     if i not in stopwords and i != ' ' and i != ',':
    #         result.append(i)
    # # fo = open("words_full.dat","w",encoding="utf-8")
    #
    # # for fi in result:
    # #     fo.write(fi)
    # #     fo.write(' ')
    # # fo.write('\n')
    # # fo.close()
    # # db.close()
    #
    # for j in range(len(result)):
    #     print(result[j], end=' ')
    #     if (j + 1) % 20 == 0:
    #         print(' ')
    # return result

def transform(dataset,n_features=1000):
    vectorizer = TfidfVectorizer(max_df=0.5, max_features=n_features, min_df=2,use_idf=True)
    X = vectorizer.fit_transform(dataset)
    return X,vectorizer

def train(X,vectorizer,true_k=8,minibatch = False,showLable = False):
    #使用采样数据还是原始数据训练k-means，
    if minibatch:
        km = MiniBatchKMeans(n_clusters=true_k, init='k-means++', n_init=1,
                             init_size=1000, batch_size=1000, verbose=False)
    else:
        km = KMeans(n_clusters=true_k, init='k-means++', max_iter=300, n_init=1,
                    verbose=False)
    km.fit(X)
    if showLable:
        print("Top terms per cluster:")
        order_centroids = km.cluster_centers_.argsort()[:, ::-1]
        terms = vectorizer.get_feature_names()
        print (vectorizer.get_stop_words())
        for i in range(true_k):
            print("Cluster %d:" % i, end='')
            for ind in order_centroids[i, :10]:
                print(' %s' % terms[ind], end='')
            print()
    result = list(km.predict(X))
    print ('Cluster distribution:')
    print (dict([(i, result.count(i)) for i in result]))
    return -km.score(X)

def out():
    '''在最优参数下输出聚类结果'''
    dataset = get_data()
    X,vectorizer = transform(dataset,n_features=1000)
    score = train(X,vectorizer,true_k=8,showLable=True)/len(dataset)
    print (score)

def Kmeans(corpus):
    tfidf_vectorizer = TfidfVectorizer(tokenizer=get_data(),lowercase=False)
    tfidf_matrix = tfidf_vectorizer.fit_transform(corpus)

    num_clusters = 8
    km_cluster = KMeans(n_clusters=num_clusters,max_iter=300,n_init=1,\
                        init='k-means++',n_jobs=1)

    result = km_cluster.fit_predict(tfidf_matrix)

    print('predicting result....')
    print(result)

def test():
    '''测试选择最优参数'''
    dataset = get_data()
    print("%d documents" % len(dataset))
    X,vectorizer = transform(dataset,n_features=500)
    true_ks = []
    scores = []
    for i in range(3,80,1):
        score = train(X,vectorizer,true_k=i)/len(dataset)
        print (i,score)
        true_ks.append(i)
        scores.append(score)
    plt.figure(figsize=(8,4))
    plt.plot(true_ks,scores,label="error",color="red",linewidth=1)
    plt.xlabel("n_features")
    plt.ylabel("error")
    plt.legend()
    plt.show()



if __name__ == '__main__':
    get_data()
    #test()
