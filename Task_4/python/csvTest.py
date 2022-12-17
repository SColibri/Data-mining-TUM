import pandas as pd

df = pd.read_csv('/home/ubuntu/sampled_embeddings.csv', delimiter=',')

with open('data.csv', 'r') as f:          # Read lines separately
    reader = csv.reader(f, delimiter='t')
    for i, line in enumerate(reader):
        print(i, line)

print(df) 