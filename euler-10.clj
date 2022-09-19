(defn sieve [size]
  (-> (let [ar (boolean-array (repeat size true))]
        (dorun (map (fn [i]
                      (when (aget ar i)
                        (dorun (map (fn [j] (aset-boolean ar j false))
                                    (range (+ i i) size i)))))
                    (range 2 (-> size Math/sqrt int inc))))
        ar)
      vec
      (assoc 0 false)
      (assoc 1 false)))

(defn count-of-primes [limit]
  (let [s (sieve limit)]
    (reduce #(+ %1 (if (s %2) 1 0))
            0 (range limit))))

(defn sum-of-primes [limit]
  (let [s (sieve limit)]
    (reduce #(+ %1 (if (s %2) %2 0))
            0 (range limit))))

(defn main []
  (time
   (let [limit 2000000
         sum (sum-of-primes limit)]
     (printf "Sum of primes below %d: %d\n" limit sum))))

(count-of-primes 1000)
(sum-of-primes 1000)
