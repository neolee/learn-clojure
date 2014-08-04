(fn [n]
  (->>
   (range 2 n)
   (filter (fn [x]
             (= 1 ((fn gcd [a b]
                     (if (= 0 b) a (gcd b (mod a b))))
                   x n))))
   count
   inc))

(fn [x]
  (count
   (filter #(= 1
               (loop [a x b %]
                 (if (zero? b) a
                     (recur b (mod a b)))))
           (range 1 (inc x)))))
