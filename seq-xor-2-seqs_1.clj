(defn seq-xor-2-seqs
  "Returns the unique values that are in one sequence but not the other."
  [x y]
  (concat
   (loop [coll1 x
	  coll2 y]
     (if (and coll1 coll2)
       (recur (rest coll1)
	      (filter (complement #(.equals % (first coll1))) coll2))
       coll2))
   (loop [coll1 y
	  coll2 x]
     (if (and coll1 coll2)
       (recur (rest coll1)
	      (filter (complement #(.equals % (first coll1))) coll2))
       coll2))))