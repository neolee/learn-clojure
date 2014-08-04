(defn job [n]
  (Thread/sleep 3000)
  (+ n 10))

(time
 (doall (map job (range 4))))

(time
 (doall (pmap job (range 4))))
