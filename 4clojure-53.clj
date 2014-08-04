(defn isubseq [v]
  (or (first
       (filter #(apply < %)
               (mapcat #(partition % 1 v) (range (count v) 1 -1)))) ; v's all sub-seq with length 10->1
      []))
