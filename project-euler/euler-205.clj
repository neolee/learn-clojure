(ns euler.p205)

; Helper, to calculate the probability for one value
; in the sum during the convolution.
(defn possibilities
  [k x y]
  (let [xks (keys x)
        mn1 (apply min xks)
        mx1 (apply max xks)
        yks (keys y)
        mn2 (apply min yks)
        mx2 (apply max yks)]
    (loop [p   0
           s1  (max mn1 (- k mx2))
           s2  (min mx2 (- k s1))]
      (if (or (< mx1 s1) (> mn2 s2))
        p
        (recur (+ p (* (x s1) (y s2))) (inc s1) (dec s2))))))
 
; The actual convolution.
(defn self-convolute
  [x n]
  (let [xks (keys x)]
    (loop [y x
           n n]
      (if (< 0 n)
        (let [yks (keys y)
              rng (range (+ (apply min xks) (apply min yks))
                         (+ (apply max xks) (apply max yks) 1))
              ny  (reduce (fn [ny k] (assoc ny k (possibilities k x y)))
                          (sorted-map) rng)]
          (recur ny (dec n)))
        y))))
 
; Generate the initial distribution of one dice roll.
(defn genrolls
  [d]
  (reduce #(assoc %1 %2 (/ 1 d)) (sorted-map) (range 1 (inc d))))
 
; Nomen est omen.
(defn integrate
  [rolls]
  (reduce #(assoc %1 %2 (+ (get %1 (dec %2) 0) (rolls %2)))
          (sorted-map) (keys rolls)))
 
; Put everything together:
; 1. Generate the distribution of a single roll.
; 2. Convolute the initial distribution n-1 times for n dices.
; 3. Integrate the distribution of Pete's sum.
; 4. Calculate the winning probability.
(defn euler-205
  [d1 n1 d2 n2]
  (let [rolls1  (genrolls d1)
        rolls1  (self-convolute rolls1 (dec n1))
        rolls2  (genrolls d2)
        rolls2  (self-convolute rolls2 (dec n2))
        rolls2  (integrate rolls2)
        wins    (map #(* (rolls1 %1) (- 1 (get rolls2 %1 0))) (keys rolls1))]
    (reduce + wins)))
 
; Here we go...
(euler-205 6 6 4 9)