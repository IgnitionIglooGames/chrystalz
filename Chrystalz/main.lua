local zone_id = 0

local function battlefield_size()
  local base = 12
  local increment = 1
  return base + (zone_id * increment)
end

local function dungeon_size()
  local base = 24
  local increment = 2
  return base + (zone_id * increment)
end

--function love.load()
  --TODO: Need to compute the action cap here
--end

function love.draw()
  love.graphics.print("Hello World", 400, 300)
end
