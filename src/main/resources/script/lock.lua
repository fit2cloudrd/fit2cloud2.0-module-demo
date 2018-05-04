local comply = redis.call('SETNX', KEYS[1], ARGV[1])
if (comply == 1) then
    redis.call('EXPIRE', KEYS[1], ARGV[2])
    return true
else
    return false
end
