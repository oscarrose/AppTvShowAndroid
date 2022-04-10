using Microsoft.EntityFrameworkCore.Migrations;

namespace FavoritesTvShowApi.Migrations
{
    public partial class Initial : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "favoritesTv",
                columns: table => new
                {
                    idFavorites = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    idUser = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: true),
                    idTvShow = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_favoritesTv", x => x.idFavorites);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "favoritesTv");
        }
    }
}
