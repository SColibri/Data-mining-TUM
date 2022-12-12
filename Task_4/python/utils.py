import numpy as np

def cosineSimilarity(vectorA, vectorB):
    """
    vectors must be 1xN dimension
    """
    return ( np.dot(vectorA, vectorB)/(np.linalg.norm(vectorA) * np.linalg.norm(vectorB)) )

# ##################