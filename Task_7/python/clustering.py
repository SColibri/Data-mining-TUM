# -----------------------------------------------------
# Clustering using K-means / k-medoids
# 
import numpy as np
import matplotlib.pyplot as plt
import random
from enum import Enum
import utils as cSim

class METRIC(Enum):
    L2 = 1
    COSINE = 2

class Cluster():
    def __init__(self):
        self.X = []
        self.k = 3
        self.features = 0
        self.max_iteration = 20
        self.classifications = []
        self.tolerance = 10**(-3)
        self.centroids = []
        self.noSamples = 0
        self.metric = METRIC.L2
        self.loss = 0

    def load(self, data):
        self.X = data
        self.noSamples = self.X.shape[0]
        self.features = self.X.shape[1]

        self.classifications = np.zeros(self.noSamples, dtype = np.float64)
        self.loss = 0
    
    def normalize_data(self):
        self.X = (self.X - np.mean(self.X, axis=0)) / np.std(self.X, axis=0)

    def set_metric(self, metric):
        self.metric = metric

    def set_random_centroids(self):
        # Check for data, if empty do nothing
        if self.noSamples == 0: 
            return
        
        # Get random points from datapoints and assign
        # them as centroids
        Indexes = np.random.choice(self.noSamples, self.k)
        self.centroids = self.X[Indexes, :]

    def get_distance(self, vectorA, vectorB):
        if self.metric == METRIC.L2:
            return np.sqrt(np.sum(np.power(vectorA - vectorB, 2)))
        elif self.metric == METRIC.COSINE:
            return 1 - cSim.cosineSimilarity(vectorA, vectorB)
        else:
            return -1 # template only

    # Computes the classification for all datapoints
    def classify(self):
        for i in range(0, self.noSamples):
            distances = np.zeros(self.k)
            for j in range(0, self.k):
                distances[j] = self.get_distance(self.X[i, :], self.centroids[j]) 
            self.classifications[i] = np.argmin(distances)
    
    def update_centroids(self):
        # Compute the new centroids and new loss
        new_centroids = np.zeros((self.k, self.features))
        new_loss = 0
        for j in range(0, self.k):
            # compute centroids
            J = np.where(self.classifications == j)
            X_C = self.X[J]
            new_centroids[j] = X_C.mean(axis=0)
            
            # Compute loss
            for i in range(0, X_C.shape[0]):
                new_loss +=  self.get_distance(X_C[i, :], new_centroids[j])

        self.centroids = new_centroids
        self.loss = new_loss
        print(self.loss)
    
    def run_iteration(self, noIteration):
        if self.noSamples == 0:
            print("No data points :'(")
            return
        
        for i in range(0, noIteration):
            self.classify()
            self.update_centroids()
    
    def plot_datapoints(self, feature_1, feature_2):
        fig = plt.figure(figsize=(8, 6))

        # Data
        plt.scatter(self.X[:, feature_1], self.X[:, feature_2], s = 3)

        # clusters
        plt.scatter(self.centroids[:, feature_1], self.centroids[:, feature_2], c="red")
        plt.show()
    
    def plot_classification_map(self, feature_1, feature_2):
        # Plot classification
        f1 = feature_1
        f2 = feature_2

        fig = plt.figure(figsize=(20, 15))
        fig.tight_layout()

        plotNo = 8
        for i in range(0, plotNo):
            for j in range(0, plotNo):
                s1 = plt.subplot(plotNo, plotNo, i*plotNo + j + 1)
                s1.scatter(self.X[:, f1 + i], self.X[:, f2 + j], c = self.classifications, s = 2)
                s1.scatter(self.centroids[:, f1 + i], self.centroids[:,f2 + j], c = "r", s = 20)

        plt.show() 