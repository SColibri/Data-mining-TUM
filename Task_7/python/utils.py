import numpy as np
from numpy.linalg import norm

def cosineSimilarity(vectorA, vectorB):
    """
    vectors must be 1xN dimension
    """
    return ( np.dot(vectorA, vectorB)/(norm(vectorA) * norm(vectorB)) )

# ##################