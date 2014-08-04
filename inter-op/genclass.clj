(gen-and-load-class 'foo.MyClass
		    :methods [['mymethod [String] String]
			      ['finalize [] Void/TYPE]])

(clojure/ns foo)
 
(defn MyClass-mymethod [this m]
  (str "Your arg was: " m))
 
(defn MyClass-finalize [this]
  (println "Finalizing " this))

(comment
  (println (.mymethod (new foo.MyClass) "foo"))
  (System/gc)
  )