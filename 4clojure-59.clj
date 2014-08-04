(defn juxt* [& fs]
  (fn [& args]
    (map #(apply % args) fs)))
