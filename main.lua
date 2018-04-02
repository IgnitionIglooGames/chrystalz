-- Set up internationalization support
local langdata = "assets/lang/"
local i18n = require("lib/i18n/i18n")
local lang = i18n()
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

function love.load()
  lang:load(langdata.."en.lua")
  lang:set_fallback("en")
  lang:set_locale("en")
  --TODO: Need to compute the action cap here
end

function love.draw()
  love.graphics.print(lang "main/crash", 400, 300)
end
