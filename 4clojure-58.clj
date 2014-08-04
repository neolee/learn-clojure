(defn comp* [& fs]
  (reduce
   (fn [f g]
     (fn [& args]
       (f (apply g args))))
   identity fs))
