; Unlike CL, symbols are just names, with no associated values. The things that are 
; more like symbols from that perspective in Clojure are vars, and it is vars that 
; are interned in namespaces. Two symbols with the same name can be distinct objects. 
; A namespace is not a set of symbols but a set of mappings from symbols to references - 
; either vars or classes.
; 
; This separation of concerns is an important part of how Clojure is a Lisp-1 while 
; still supporting defmacro semi-hygienically. The reader reads plain symbols and does 
; no interning. The compiler resolves names (symbols) in the compilation namespace in 
; order to find vars/classes. def interns new vars, and import/refer/use can make new mappings.
; 
; ns is the preferred way to define/setup a namespace (think defpackage) - it is more 
; declarative. But it is intended to be used once only. Using in-ns as you did is the 
; right way to change namespaces at the repl - ns is not for that.
; 
; (by Rich Hickey)

(resolve 'concat)
(resolve 'do)
(defn do [] (+ 1 2))
(do)
(user/do)

(create-ns 'test)
(count (ns-map 'test))
(count (ns-interns 'test))
(doseq x (ns-map 'test) (printf "%s: %s\n" (first x) (frest x)))

(in-ns 'test)
(clojure/defn three [] 3)
(in-ns 'user)
(count (ns-map 'test))
(count (ns-interns 'test))

(ns-unmap 'test 'three)
(defn seq-xor [& seqs]
  (seq (second
	(reduce (fn [[all ret] x]
		  (if (contains? all x)
		    [all (disj ret x)]
		    [(conj all x) (conj ret x)]))
		[#{} #{}] (mapcat distinct seqs)))))

(doseq x (seq-xor (ns-map 'test) (seq-xor (ns-interns 'clojure) (ns-map 'clojure))) (printf "%s: %s\n" (first x) (frest x)))

(. Thread (sleep 1000))

(in-ns 'test)
(clojure/import '(javax.swing JFrame))
(in-ns 'user)
(ns-resolve 'test 'JFrame)
