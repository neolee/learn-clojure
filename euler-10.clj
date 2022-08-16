(defn sieve [size]
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

(defn main []
  (time
   (let [limit 2000000
         s (sieve limit)]
     (reduce #(+ %1 (if (s %2) %2 0))
             0 (range limit)))))
