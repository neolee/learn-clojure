(def board-size 8)

(defn qtest [qcol qvect]
  "Test if position `qcol` is okay for addition to `qvect`"
  (defn f [x] (let [[row col] x]
                (or
                 (= qcol col)
                 (= qcol (- col (+ row  1)))
                 (= qcol (+ col (+ row  1))))))
  (empty? (filter f (map-indexed vector (rseq qvect)))))

(defn qsolve [qvect]
  "Recursively find solution based on initial `qvect` board config"
  (doseq [p (range board-size)]
    (if (qtest p qvect)
      (let [qnew (conj qvect p)]
        (if (= (count qnew) board-size) (println qnew) (qsolve qnew))))))

(qsolve [])
