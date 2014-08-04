(defstruct employee :name :id)
(struct employee "Mr. X" 10)
(struct-map employee :id 20 :name "Mr. Y")
(def a (struct-map employee :id 20 :name "Mr. Y"))
(def b (struct employee "Mr. X" 10))
(comment
  (:name a)
  (:id b)
  (b :id)
  (b :name)
  )

(def e-str (struct employee "John" 123))
(def e-name (accessor employee :name))
(comment
  e-str
  (e-name e-str)
  )

(def b1 (assoc b :function :engineer))
(def b2 (dissoc b1 :function))
(comment
  b1
  b2
  (dissoc b2 :name)
  )

(assoc a :name "New Name")
(def a1 (assoc a :name "Another New Name"))
(comment
  a
  a1
  )