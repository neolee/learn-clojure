(defn rd
  ([f coll]
     (lazy-seq
      (if-let [s (seq coll)]
        (rd f (first s) (rest s))
        (list (f)))))
  ([f init coll]
     (cons init
           (lazy-seq
            (when-let [s (seq coll)]
              (rd f (f init (first s)) (rest s)))))))
