(comment
  (try (/ 1 0)
       (catch Exception e (prn "in catch"))
       (finally (prn "in finally")))

  (try (/ 1 0)
       (catch ArithmeticException e (prn "in catch"))
       (finally (prn "in finally")))

  (try (/ 1 0)
       (catch IllegalArgumentException e (prn "in catch"))
       (finally (prn "in finally")))
  )

(gen-and-load-class 'user.UserException :extends Exception)
 
(defn user-exception-test []
  (try
   (throw (new user.UserException "msg: user exception was here!!"))
   (catch user.UserException e
     (prn "caught exception" e))
   (finally (prn "finally clause invoked!!!"))))