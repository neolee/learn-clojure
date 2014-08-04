(fn grp-seq1 [f coll]
  (into {}
        (map #(vector (f (first % )) (vec %))
             (partition-by f (sort coll)))))

(fn grp-seq2 [f coll]
  (apply merge-with concat (map (fn [x] {(f x) [x]}) coll)))
