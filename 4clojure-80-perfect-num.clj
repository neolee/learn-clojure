(defn pnum? [x]
  (let [divisors (filter #(zero? (mod x %)) (range 1 x))]
    (= x (reduce + divisors))))
