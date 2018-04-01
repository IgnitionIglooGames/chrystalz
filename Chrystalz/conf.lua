--[[
Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
]]

function love.conf(t)
    t.identity = "studio.ignitionigloogames.chrystalz"
    t.version = "0.10.1"
    t.accelerometerjoystick = true
    t.window.title = "Chrystalz"
    t.window.icon = "assets/images/logo/iconlogo.png"
    t.window.width = 800
    t.window.height = 600
    t.window.borderless = false
    t.window.resizable = false
    t.window.fullscreen = false
    t.window.fullscreentype = "desktop"
    t.window.highdpi = false
end