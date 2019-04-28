from jieba import analyse

tfidf = analyse.extract_tags

for line in open("words_full.dat",encoding="utf-8"):
    text = line

    keywords = tfidf(text,allowPOS=('ns','nr','nt','nz','nl','n', 'vn','vd','vg','v','vf','a','an','i'))

    result=[]

    for keyword in keywords:
        result.append(keyword)

    fo = open("keywords.dat","w",encoding="utf-8")

    for j in result:
        fo.write(j)
        fo.write(' ')
    fo.write('\n')
    fo.close()
    for z in result:
        print(z)