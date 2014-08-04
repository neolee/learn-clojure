(use 'clojure.contrib.lazy-seqs)

(defn div? [n d]
  (zero? (rem n d)))
 
(defn prime? [n]
  (and (< 1 n) (not-any? (partial div? n) (take-while #(<= (* % %) n) primes))))
 
(defn eleven_primes_start_with [n]
  (take 11 (for [m primes :when (>= m n)] m)))

(def eleven_primes (map eleven_primes_start_with primes))
(def eleven_primes_sums (map #(reduce + %) eleven_primes))
(def magic_primes (for [n eleven_primes_sums :when (prime? n)] n))

(println (nth magic_primes 1000))