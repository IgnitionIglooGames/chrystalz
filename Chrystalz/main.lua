--[[
Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
]]

zone_id = 0

function battlefield_size()
  local base = 12
  local increment = 1
  return base + (zone_id * increment)
end

function dungeon_size()
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
