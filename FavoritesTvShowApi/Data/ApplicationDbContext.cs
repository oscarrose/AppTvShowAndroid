using Microsoft.EntityFrameworkCore;

namespace FavoritesTvShowApi.Data
{
    public class ApplicationDbContext : DbContext
    {
        public DbSet<EntitesFavorite> favorites { get; set; }
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {

        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<EntitesFavorite>(entity =>
            {
                entity.ToTable("favoritesTv");
                entity.HasKey(a => a.idFavorites);
                entity.Property(a => a.idUser).HasMaxLength(50);
                entity.Property(a=>a.idUser).HasMaxLength(50);

            });
        }

    }

    public class EntitesFavorite{

       public int idFavorites { get; set; }
        public string idUser { get; set; }
        public string idTvShow { get; set; }
        

    }
}
