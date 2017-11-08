(defn div? [n d]
  (zero? (rem n d)))

(defn smallest-prime-factor [number]
  (loop [n number d 2]
    (cond (> d (int (Math/sqrt number))) n
          (= n d) n
          (div? n d) d
          true (recur n (inc d)))))

(defn prime? [n]
  (= n (smallest-prime-factor n)))

(def primes (lazy-cat '(2 3)
                      (filter prime? (take-nth 2 (iterate inc 5)))))

(defn eleven_primes_start_with [n]
  (take 11 (for [m primes :when (>= m n)] m)))

(def eleven_primes (map eleven_primes_start_with primes))
(def eleven_primes_sums (map #(reduce + %) eleven_primes))
(def magic_primes (for [n eleven_primes_sums :when (prime? n)] n))

(println (nth magic_primes 1000))
