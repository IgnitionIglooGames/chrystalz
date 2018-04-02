-- Set up internationalization support
local language_data = "assets/lang/"
local languages = require("lib/i18n/i18n")
languages.set_fallback(language_data + "en")
languages.set_locale(language_data + "en")
str_crash = languages.get("main/crash")

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
  love.graphics.print(str_crash, 400, 300)
end
