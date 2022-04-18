using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using FavoritesTvShowApi.Data;

namespace FavoritesTvShowApi.Controller
{
    [Route("api/[controller]")]
    [ApiController]
    public class EntitesFavoritesController : ControllerBase
    {
        private readonly ApplicationDbContext _context;

        public EntitesFavoritesController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: api/EntitesFavorites/5
        [HttpGet("{userId}")]
        public async Task<ActionResult<IEnumerable<EntitesFavorite>>> GetEntitesFavorite(string userId)
        {
            var entitesFavorite = await _context.favorites.Where(e=>e.idUser == userId).ToListAsync();

            if (entitesFavorite==null)
            {
                
                return NotFound();
            }

            return entitesFavorite;
        }


        // POST: api/EntitesFavorites
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<EntitesFavorite>> PostEntitesFavorite(EntitesFavorite entitesFavorite)
        {
            
            _context.favorites.Add(entitesFavorite);
            await _context.SaveChangesAsync();

            return entitesFavorite;
        }

        // DELETE: api/EntitesFavorites/5
        [HttpDelete("{idTvShow}")]
        public async Task<IActionResult> DeleteEntitesFavorite(String idTvShow, String idUser)
        {
            var entitesFavorite = await _context.favorites.Where(e => e.idTvShow == idTvShow && e.idUser==idUser).ToListAsync();
            //await _context.favorites.FindAsync(idTvShow);
            if (entitesFavorite == null)
            {
                return NotFound();
            }

            _context.favorites.RemoveRange(entitesFavorite);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool EntitesFavoriteExists(string id)
        {
            return _context.favorites.Any(e => e.idTvShow ==id );
        }
    }
}
