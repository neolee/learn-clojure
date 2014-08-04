(fn [board]
  (let [i [0 1 2]
        c (take 12 (cycle i))
        p (flatten (map #(repeat 3 %) i))
        zip #(map vector %1 %2)
        win? (fn [w]
               (some
                (fn [x] (every? #(= w (get-in board %)) x))
                (partition
                 3 (into (zip (into i p) c) (zip c (into (reverse i) p))))))]
    (cond
     (win? :x) :x
     (win? :o) :o)))

(fn [board]
  (letfn [(check-horizonal [board]
            (first (flatten (filter #(every? #{:x :o} %)
                                    (filter #(= 1 (count (set %))) board)))))]
    (let [h (check-horizonal board)]
      (if
        h
        h
        (let [v (check-horizonal (apply (partial map #(vec [%1 %2 %3])) board))]
          (if
            v
            v
            (let [fd (check-horizonal [[(first (first board))
                                        (second (second board))
                                        (last (last board))]])]
              (if
                fd
                fd
                (check-horizonal [[(last (first board))
                                   (second (second board))
                                   (first (last board))]])))))))))

(fn [board]
  (let [rows board
        columns (apply map list board)
        diagonals [[(first (first board)) (second (second board)) (nth (nth board 2) 2)]
                   [(first (nth board 2)) (second (second board)) (nth (first board) 2)]]
        lines (concat rows columns diagonals)
        matches (filter #(apply = %) lines)
        winner (first (first (filter #(not-any? #{:e} %) matches)))]
    winner))
