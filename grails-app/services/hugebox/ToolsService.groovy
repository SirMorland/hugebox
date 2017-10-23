package hugebox

class ToolsService
{
    String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~"

    String generatePassword()
    {
        String password = ""
        for(int i = 0; i < 127; i++)
        {
            password += takeRandom(CHARACTERS)
        }
        return password
    }

    def takeRandom(Object[] array)
    {
        array[random(0, array.length)]
    }

    int random(int a, int b)
    {
        Random r = new Random()
        r.nextInt(b - a) + a
    }
}
