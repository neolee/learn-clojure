(fn [s]
  (->>
   (re-seq #"\d+" s)
   (map #(Integer/parseInt %))
   (filter (fn [x] (let [r (int (Math/sqrt x))]
                    (= x (* r r)))))
   (interpose ",")
   (apply str)))
