(def fs-flags [:global-read :global-write :group-read :group-write :owner-read :owner-write])

(def bitmap (zipmap fs-flags
                    (map (partial bit-shift-left 1) (range))))

(defn permissions-int [& flags]
  (reduce bit-or 0 (map bitmap flags)))

(def owner-only (permissions-int :owner-read :owner-write))
(def read-only (permissions-int :owner-read :group-read :global-read))

(defn able-to? [permissions flag]
  (not= 0 (bit-and permissions (bitmap flag))))
