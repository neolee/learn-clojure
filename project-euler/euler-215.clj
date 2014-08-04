(ns euler.p215
  (require [clojure.set :as set]))

(defn valid-joint?
  [x]
  (< 2 (bit-and x 7)))

(defn cracks
  [x]
  (let [s (bit-shift-left x 1)]
    (vector s (bit-or s 1))))

(defn joints
  [len]
  (loop [r   [1]
         len (- len 2)]
    (if (< 2 len)
      (let [cands (mapcat cracks r)]
        (recur (filter valid-joint? cands) (dec len)))
      (let [cands (map #(bit-or (bit-shift-left % 2) 2) r)]
        (filter #(and (valid-joint? %)
                      (valid-joint? (bit-shift-right % 1)))
                cands)))))

(defn split-joint
  [len x]
  (loop [r   [0 0]
         n   [1 0]
         len (dec len)]
    (if (<= 0 len)
      (recur (if (bit-test x len)
               (vector (bit-or (bit-shift-left (r 0) 1) (n 0))
                       (bit-or (bit-shift-left (r 1) 1) (n 1)))
               (vector (bit-shift-left (r 0) 1)
                       (bit-shift-left (r 1) 1)))
             (if (bit-test x len)
               (vector (n 1) (n 0))
               n)
             (dec len))
      r)))

(defn line-graph
  [len]
  (let [js  (joints len)
        sps (map #(vec (split-joint len %)) js)
        all (reduce #(set/union %1 %2) #{} sps)]
    (reduce (fn [graph [l1 l2]]
              (update-in (update-in graph [l1] conj l2)
                         [l2] conj l1))
            (apply hash-map (mapcat (fn [l] [l #{}]) all))
            sps)))

(defn count-ways
  [component graph height]
  (if (< 2 (count component))
    (loop [i 1
           c (apply hash-map (interleave component (repeat 1)))]
      (if (< i height)
        (recur (inc i) (reduce (fn [nc n]
                                 (let [nbs (map #(c %) (graph n))
                                       cnt (reduce + nbs)]
                                   (assoc nc n cnt)))
                               c component))
        (reduce + (vals c))))
    2))

(defn components
  [graph]
  (let [track (fn track [n c]
                (if (contains? c n)
                  c
                  (reduce #(track %2 %1) (conj c n) (graph n))))]
    (loop [nodes (into #{} (keys graph))
           comps nil]
      (if (< 0 (count nodes))
        (let [c (track (first nodes) #{})]
          (recur (set/difference nodes c) (conj comps c)))
        comps))))

(defn euler-215
  [width height]
  (let [graph (line-graph (dec width))
        comps (components graph)]
    (reduce + (map #(count-ways % graph height) comps))))

(time euler-215)
