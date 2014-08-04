(keep even? (range 1 10))

(keep #(if (odd? %) %) (range 10))
(map #(if (odd? %) %) (range 10))
(for [ x (range 10) :when (odd? x)] x)
(keep #(if(even? %) %) (range 10))
(map #(if (even? %) %) (range 10))
(for [ x (range 10) :when (even? x)] x)

;; Sieve of Eratosthenes by using 'keep'
(defn keep-mcdr [f coll]
  (lazy-seq
   (when-let [x (first coll)]
     (cons x  (keep-mcdr f (f x (rest coll)))))))

(defn prime-number [n]
  (cons 1
	(keep-mcdr
	 (fn[x xs] (if (not-empty xs)
                    (keep #(if-not (zero? (rem % x)) %)
                          xs)))
	 (range 2 n))))
