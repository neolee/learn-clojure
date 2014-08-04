;; From Clojure mailing list: http://groups.google.com/group/clojure/browse_thread/thread/292fb3ab568c1168

; Stuart Halloway suggested the following alternative (using Sets for the 2-sequence XOR function)
; and commented: "Not sure this is more idiomatic, though. And I guess it would perform worse with huge collections..."
(defn seq-xor-2-seqs 
  "Returns the unique values that are in one sequence but not the other." 
  [x y] 
  (let [x (into #{} x) 
        y (into #{} y)] 
    (lazy-cat (clojure.set/difference x y) 
              (clojure.set/difference y x))))

; Then, Chouser posted the following:
; "If I'm reading thing's correctly, Bill's solution is O(n^2), while Stuart's is O(n*log(n)) or better. It looks like
; difference iterates over one seq and for each item does a lookup (nearly constant time) on the the other, although
; copying into sets may use more memory. Here's another approach to do all the seqs at once: "
(defn seq-xor 
  "Returns unique values that are in one sequence but not the others." 
  [& seqs] 
  (let [obj-cnt (reduce (fn [acc s] 
                          (merge-with + acc (into {} (for [i s] {i 1})))) 
                        {} seqs)] 
    (for [[obj cnt] obj-cnt :when (== cnt 1)] 
      obj)))
; What a concise, elegant solution (using Clojure's version of List Comprehensions)! 

; Then, Rich Hickey sent his version of the code (using Sets):
(alias 'set 'clojure.set)
(defn set-xor [& sets]
  (second (reduce
	   (fn [[all ret] s]
	     [(set/union all s) (set/union (set/difference ret s)
					   (set/difference s all))])
	   [#{} #{}] sets)))
(defn seq-xor [& seqs] (seq (apply set-xor (map set seqs))))

; Rich then followed this with another variation (inspired by Chouser's, one pass, no counting):
(defn seq-xor [& seqs]
  (seq (second
	(reduce (fn [[all ret] x]
		  (if (contains? all x)
		    [all (disj ret x)]
		    [(conj all x) (conj ret x)]))
		[#{} #{}] (mapcat distinct seqs)))))

; Woo hoo! All good alternatives and nice to see how other people would do the same thing - thanks
; everyone! Rich also provided an excellent summary for this post and I'll close on that:
;   "One take away from all of these is that use of sets, maps and vectors is very idiomatic Clojure,
;    and the sooner you can add them to the lists in your toolbox the more productive you'll be with Clojure."