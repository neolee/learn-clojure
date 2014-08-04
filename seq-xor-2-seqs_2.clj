(in-ns 'learning)
(clojure/refer 'clojure)

(defn- seq-xor-2-seqs
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

(defn- seq-and-2-seqs
  "Returns the values that are in both sequences."
  [x y]
  (loop [coll1 x
	 coll2 y
	 result nil]
    (if (and coll1 coll2)
      (recur (rest coll1)
	     coll2
	     (concat result (filter #(.equals % (first coll1)) coll2)))
      result)))

(defn seq-xor
  "Returns unique values that are in one sequence but not the others."
  ([] nil)
  ([x] (distinct x))
  ([x & ys]
     (loop [x1 x
	    ys1 ys
	    old-dups nil]
       (let [coll1 x1
	     coll2 (first ys1)
	     coll-rest (rest ys1)
	     result (distinct (seq-xor-2-seqs coll1 (seq-xor-2-seqs old-dups coll2)))
	     dups (distinct (concat old-dups (seq-and-2-seqs coll1 coll2)))]
	 (if (and coll1 coll-rest)
	   (recur result
		  (concat (list (concat dups (first coll-rest))) (rest coll-rest))
		  dups)
	   result)))))

(comment
  (seq-xor)
  (seq-xor '(a b))
  (seq-xor '(a b b c))
  (seq-xor '((a b) (c d)))
  (seq-xor '((a b) (c d)) '(a b))
  (seq-xor '(a e) '(a b) '((a b) (c d)) '(a b))
  (seq-xor '(a b c d e f g) '((a b) (b d)) '(a b) '(c d))
  (seq-xor '(a b c d e f g) '(a b) '(b d) '(a b) '(c d))
  (seq-xor [1 2 3] [2 3] [4 5])
  (seq-xor [1 2 3 7] [2 3] [5 6] [7])
  (seq-xor {:a 1 :b 2} {:a 1 :b 4} {:a 3 :b 2})
  )