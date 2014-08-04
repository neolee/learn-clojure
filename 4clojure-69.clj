(fn [f & args]
  (reduce (fn[map1 map2]
            (reduce (fn [m [k v]]
                      (if-let [vv (m k)]
                        (assoc m k (f vv v))
                        (assoc m k v)))
                    map1 map2))
          args))

(fn [f & maps]
  (letfn [(step
            [m [k v]]
            (if (contains? m k)
              (assoc m k (f (get m k) v))
              (assoc m k v)))]
    (reduce #(reduce step %1 %2) maps)))

;; Natural solution
(fn [f & maps]
  (let [g (group-by first (apply concat (map #(apply list %) maps)))]
    (zipmap (keys g)
            (map #(reduce f (map second %)) (vals g)))))
