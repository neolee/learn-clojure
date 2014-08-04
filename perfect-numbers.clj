(defn divisors [n]
  (filter #(= (rem n %) 0) (range 1 (inc (/ n 2 )))))

(defn sum [s]
  (reduce + s))

(defn perfect? [n]
  (= n (sum (divisors n))))

(defn perfect-numbers []
  (filter perfect? (nnext (range))))

(time (println (take 4 (perfect-numbers))))
