(defn div? [n d]
  (zero? (rem n d)))

(defn smallest-prime-factor [number]
  (loop [n number d 2]
    (cond (> d (int (Math/sqrt number))) n
          (= n d) n
          (div? n d) d
          :else (recur n (inc d)))))

(defn prime-factors [number]
  (loop [x 1 result [] a number]
    (if (or (> x a) (= a 1))
      result
      (let [y (smallest-prime-factor a)]
        (recur y
               (conj result y)
               (/ a y))))))

(defn prime? [n]
  (= n (smallest-prime-factor n)))

(def primes (lazy-cat '(2 3)
                      (filter prime? (take-nth 2 (iterate inc 5)))))

(defn eleven_primes_start_with [n]
  (take 11 (for [m primes :when (>= m n)] m)))

(def eleven_primes (map eleven_primes_start_with primes))
(def eleven_primes_sums (map #(reduce + %) eleven_primes))
(def magic_primes (for [n eleven_primes_sums :when (prime? n)] n))

(prime-factors 99128363871263)
(take 10 (drop 10 magic_primes))
