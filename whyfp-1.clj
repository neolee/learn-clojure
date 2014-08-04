(defn sum [s]
  (reduce + s))

(defn multiple [s]
  (reduce * s))

(defn op [a b]
  (+ a (* b 2)))

(defn doublesum1 [s]
  (reduce op 0 s))

(defn doublesum2 [s]
  (reduce + (map (fn [x] (* x 2)) s)))


