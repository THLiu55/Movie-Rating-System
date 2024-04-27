def singleton(cls):
    instances = {}
    def wrapper(*args, **kwargs):
        if cls not in instances:
            instances[cls] = cls(*args, **kwargs)
        return instances[cls]
    return wrapper

@singleton
class Classifier:
    def __init__(self, model_path, tfidf_path):
        import joblib
        self.loaded_model = joblib.load(model_path)
        self.tf = joblib.load(tfidf_path)

    def predict(self, text):
        pro_text = [text]
        X_new_tf = self.tf.transform(pro_text).toarray()
        y_new_predict = self.loaded_model.predict(X_new_tf)
        return y_new_predict[0]
    
