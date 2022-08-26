(defn sieve1 [size]
  (-> (reduce (fn [prev i]
                (if (prev i)
                  (vec (reduce #(assoc %1 %2 false)
                               prev
                               (range (+ i i) size i)))
                  prev))
              (vec (repeat size true))
              (range 2 (-> size Math/sqrt int inc)))
      vec
      (assoc 0 false)
      (assoc 1 false)))

(defn sieve2 [size]
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

(defn count-primes [limit]
  (time
   (let [s (sieve2 limit)]
     (reduce #(+ %1 (if (s %2) 1 0))
             0 (range limit)))))

(defn main []
  (time
   (let [limit 2000000
         s (sieve2 limit)]
     (reduce #(+ %1 (if (s %2) %2 0))
             0 (range limit)))))
