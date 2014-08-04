require 'prime'

puts Prime.lazy.take(100000).force.last
