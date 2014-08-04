(fn minpath
  ([triangle] (minpath triangle 0))
  ([triangle index]
     (if (empty? triangle) 0
         (min
          (+ (nth (first triangle) index) (minpath (rest triangle) index))
          (+ (nth (first triangle) index) (minpath (rest triangle) (inc index)))
          ))))

#(apply min
        (reduce
         (fn [a v] (map min
                       (map + (cons Double/POSITIVE_INFINITY a) v)
                       (map + (concat a [Double/POSITIVE_INFINITY]) v)))
         %))
