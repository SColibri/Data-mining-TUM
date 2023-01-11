import utils as group2
import numpy as np
# Can also remove 1 from similarities sum to account for metoid cosine with self, and not remove it from full list

class MetoidCluster():
    # initialize with a given metoid vector and a list of vectors
    def __init__(self, metoid, cluster_list):
        self.metoid = metoid
        self.cluster = np.array(cluster_list)
        self.cosine_similarity_sum = 0
        self.cluster_similarities = []

    def get_metoid(self):
        return self.metoid

    def set_metoid(self, new_metoid):
        self.metoid = new_metoid
    

    def get_cluster(self):
        return self.cluster

    def set_cluster(self, vec_list):
        self.cluster = vec_list

    def get_cluster_similarities(self):
        return self.cluster_similarities

    def add_vec_to_cluster(self, np_vector):
        self.cluster = np.vstack((self.cluster, np_vector))

    def pop_vec_from_cluster(self, idx):
        # Since its an np array, there is no pop. Has to be done manually
        vec = self.cluster[idx]
        self.cluster = np.vstack( (self.cluster[:idx], self.cluster[idx + 1:]))
        return vec


    def cos_similarity(self, given_vector):
        return group2.cosineSimilarity(given_vector, vectorB = self.metoid)
    
    def sum_all_cos_similarities(self):
        self.cluster_similarities = list(map(self.cos_similarity, self.cluster))
        self.cosine_similarity_sum = sum(self.cluster_similarities)
    
    def get_cos_similarity_sum(self):
        return self.cosine_similarity_sum