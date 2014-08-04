(fn [coll]
  (->> (group-by frequencies coll)
       (vals)
       (filter #(> (count %) 1))
       (map set)
       set))
