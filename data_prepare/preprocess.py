# read the column "review" from IMDB Dataset.csv, remove the tags, convert to lowercase, remove punctuation, and save the to a Json file.

import pandas as pd
import re
import string

tdf = pd.read_csv("IMDB Dataset.csv")
df= tdf.iloc[:10000,:]

df.drop_duplicates(inplace = True)

def remove_tags(raw_text):
    cleaned_text = re.sub(re.compile('<.*?>'),"",raw_text)
    return cleaned_text

df['review'] = df['review'].apply(remove_tags)

df['review'] = df['review'].apply(lambda x:x.lower())

exclude = string.punctuation

def remove_punctuation(text):
    for c in exclude:
        text = text.replace(c,'')
    return text

df['review'] = df['review'].apply(remove_punctuation)

X = df.iloc[:,0:1]

X.to_json('IMDB Dataset.json',orient='records')

print("Data saved to IMDB Dataset.json")


# load the json file and enumerate the first 5 records.

import json

with open('IMDB Dataset.json') as f:
    data = json.load(f)

import random
ones, twos, threes = 0, 0, 0
for i in range(len(data)):
    if i % 3 == 0:
        data[i]['movie_id'] = 1
        ones += 1
    elif i % 3 == 1:
        data[i]['movie_id'] = 2
        twos += 1
    else:
        data[i]['movie_id'] = 3
        threes += 1
print(f"Number of records for movie 1: {ones}")
print(f"Number of records for movie 2: {twos}")
print(f"Number of records for movie 3: {threes}")

# reorder the data randomly:
random.shuffle(data)

# split　the data into 3 parts equally and save them to 3 different json files.

part = len(data) // 3

with open('IMDB Dataset1.json', 'w') as f:
    json.dump(data[:part], f)

with open('IMDB Dataset2.json', 'w') as f:
    json.dump(data[part:2*part], f)

with open('IMDB Dataset3.json', 'w') as f:
    json.dump(data[2*part:], f)

print("Data saved to IMDB Dataset1.json, IMDB Dataset2.json, IMDB Dataset3.json")


# load the json files

with open('IMDB Dataset1.json') as f:
    data1 = json.load(f)
    print(f"Number of records in IMDB Dataset1.json: {len(data1)}")

with open('IMDB Dataset2.json') as f:
    data2 = json.load(f)
    print(f"Number of records in IMDB Dataset2.json: {len(data2)}")

with open('IMDB Dataset3.json') as f:
    data3 = json.load(f)
    print(f"Number of records in IMDB Dataset3.json: {len(data3)}")


# check unique replys in total 

replys = set()
for d in data1:
    replys.add(d['review'])
for d in data2:
    replys.add(d['review'])
for d in data3:
    replys.add(d['review'])
print(f"Number of unique replys in total: {len(replys)}")