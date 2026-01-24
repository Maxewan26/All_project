from init_screen import *

"""Sert pour défnir la taille des icones"""
screen_info = pygame.display.Info()
screen_width = screen_info.current_w
screen_height = screen_info.current_h

liste_drag_dead = []
liste_power = ['fire', 'wind', 'earth', 'water', 'poison', 'metal', 'energy', 'void', 'light', 'shadow', "legendary",
               "disease", "love"]
power_damage = (
'fire', 'wind', 'poison', 'energy', 'shadow', "legendary")  #Pas void, light, et disease, ils sont indépendant
power_support = ('earth', 'water', 'metal')

dico_type_dragon = {
    "": [1, 1, 1],
    "combattant": [1, 1, 1],
    "assassin": [0.75, 1.5, 0.75],
    "tank": [1.5, 0.75, 0.75],
    "healer": [0.75, 0.75, 1.5],
    "dead" : [0.002,0,0],
    "Boss" : [3,3,3],
    "Bassassin": [2.25,4.5,2.25],
    "Btank" : [4.5,2.25,2.25],
    "Bhealer" : [2.25,2.25,4.5],
}

taille_icon_power = (50, 50)

dico_image_effect_power = {
    'fire': pygame.transform.scale(pygame.image.load(path_for_sprite + "image_element/fire.png"), taille_icon_power),
    'wind': pygame.transform.scale(pygame.image.load(path_for_sprite + "image_element/wind.png"), taille_icon_power),
    'earth': pygame.transform.scale(pygame.image.load(path_for_sprite +"image_element/earth.png"), taille_icon_power),
    'water': pygame.transform.scale(pygame.image.load(path_for_sprite +"image_element/water.png"), taille_icon_power),
    'poison': pygame.transform.scale(pygame.image.load(path_for_sprite +"image_element/poison.png"), taille_icon_power),
    'metal': pygame.transform.scale(pygame.image.load(path_for_sprite +"image_element/metal.png"), taille_icon_power),
    'energy': pygame.transform.scale(pygame.image.load(path_for_sprite +"image_element/energy.png"), taille_icon_power),
    'void': pygame.transform.scale(pygame.image.load(path_for_sprite +"image_element/void.png"), taille_icon_power),
    'light': pygame.transform.scale(pygame.image.load(path_for_sprite +"image_element/light.png"), taille_icon_power),
    'shadow': pygame.transform.scale(pygame.image.load(path_for_sprite +"image_element/shadow.png"), taille_icon_power),
    'legendary': pygame.transform.scale(pygame.image.load(path_for_sprite +"image_element/legendary.png"), taille_icon_power),
    'disease': pygame.transform.scale(pygame.image.load(path_for_sprite +"image_element/disease.png"), taille_icon_power),
    'love' : pygame.transform.scale(pygame.image.load(path_for_sprite + "image_element/love.png"), taille_icon_power),
}


class Dragon:
    def __init__(self, name, health, damage, support, power, type_dragon, index):
        self.name = name
        self.health = health * type_dragon[0]
        self.health_max = health * type_dragon[0]
        self.damage = damage * type_dragon[1]
        self.care_multiplier = type_dragon[2]
        self.support = support * self.care_multiplier
        self.support_max = self.support
        self.power = power
        self.index = index
        self.round_poison = 0
        self.lvl_poison = 0
        self.round_earth = 0
        self.lvl_earth = 0
        self.cooldown_water = 0
        self.round_metal = 0
        self.lvl_metal = 0
        self.round_wind = 0
        self.lvl_wind = 0
        self.stun = 0
        self.nb_of_stack_light = 0
        self.lvl_light = 25 * type_dragon[1]
        self.nb_of_stack_shadow = 0
        self.round_legendary = 0
        self.sick = False
        self.lvl_sick = 0
        self.in_love = [0,0]
        self.list_effect = []
        self.list_effect_actif = []
        self.dico_power = {
            'fire': self.fire,
            'wind': self.wind,
            'earth': self.earth,
            'water': self.water,
            'poison': self.poison,
            'metal': self.metal,
            'energy': self.energy,
            'void': self.void,
            'light': self.light,
            'shadow': self.shadow,
            'legendary': self.legendary,
            'disease': self.disease,
            'love' : self.love
        }

        self.dico_effect = {
        }

    def what_effect(self):
        self.list_effect = [self.round_wind, self.round_earth, self.cooldown_water, self.round_poison, self.round_metal,
                            self.nb_of_stack_light, self.nb_of_stack_shadow, self.round_legendary, self.sick, self.in_love[0]]
        self.dico_effect = {
            "0": [dico_image_effect_power["wind"], f"{self.lvl_wind}"],
            "1": [dico_image_effect_power["earth"], f"{self.lvl_earth}"],
            "2": [dico_image_effect_power["water"], f"{self.cooldown_water}"],
            "3": [dico_image_effect_power["poison"], f"{self.lvl_poison}"],
            "4": [dico_image_effect_power["metal"], f"{self.lvl_metal}"],
            "5": [dico_image_effect_power["light"], f"{self.nb_of_stack_light}"],
            "6": [dico_image_effect_power["shadow"], f"{self.nb_of_stack_shadow}"],
            "7": [dico_image_effect_power["legendary"], f"{self.round_legendary}"],
            "8": [dico_image_effect_power["disease"], f"{self.lvl_sick}"],
            "9": [dico_image_effect_power["love"], f"{self.in_love[0]}"]
        }
        for i in range(len(self.list_effect)):
            if self.list_effect[i] > 0:
                self.list_effect_actif.append(self.dico_effect[str(i)])

    def is_affect_wind(self):
        if self.round_wind > 0:
            print("Réduction de vent appliqué")
            return self.lvl_wind
        else:
            return 0

    def is_affect_poison(self):
        if self.round_poison > 0:
            self.health -= self.lvl_poison
            self.round_poison -= 1
            print("Poison appliqué")

    def is_protect_earth(self):
        if self.round_earth > 0:
            print("Protection de terre appliqué")
            return self.lvl_earth
        else:
            return 0

    def is_protect_metal(self, attacker):
        if self.round_metal > 0:
            damage_dealt = round((
                                             self.lvl_metal - attacker.is_protect_earth() - attacker.is_protect_shadow()) * attacker.is_affect_legendary())
            if damage_dealt < 0:
                damage_dealt = 0
            attacker.health -= damage_dealt
            print("Vengeance du métal appliqué")

    def is_protect_shadow(self):
        return 8 * self.nb_of_stack_shadow

    def is_affect_legendary(self):
        if self.round_legendary > 0:
            return 1.4
        else:
            return 1

    def is_sick(self):
        if self.sick:
            self.health -= self.lvl_sick
        print('Maladie appliqué')

    def is_affect_stun(self):
        if self.stun > 0:
            return True

    def attack(self, l_enemy, index_target):
        damage_dealt = round((self.damage - l_enemy[index_target].is_protect_earth()
                              - l_enemy[index_target].is_protect_shadow() - self.is_affect_wind()) * l_enemy[
                                 index_target].is_affect_legendary())
        if damage_dealt < 0:
            damage_dealt = 0
        l_enemy[index_target].health -= damage_dealt
        l_enemy[index_target].is_protect_metal(self)
        return damage_dealt

    def fire(self, l_enemy, index_target):
        for i in range(len(l_enemy) - 1):
            if i != index_target:
                damage_dealt = round(((self.damage * 0.55) - l_enemy[i].is_protect_earth()
                                      - l_enemy[i].is_protect_shadow() - self.is_affect_wind()) * l_enemy[
                                         i].is_affect_legendary())
                if damage_dealt < 0:
                    damage_dealt = 0
                l_enemy[i].health -= damage_dealt
                l_enemy[i].is_protect_metal(self)
        print("Attaque de feu réussi\n")

    def wind(self, l_enemy, index_target):
        l_enemy[index_target].round_wind = 3
        """ 3 pas 2 car la valeur est directement retiré au tour de
         l'adversaire, cela ne ferait donc qu'un tour d'affaiblissement"""
        max_stat = max(self.damage,self.support)
        l_enemy[index_target].lvl_wind = round(max_stat * 0.6)
        print("Attaque de vent réussi\n")

    def earth(self, l_ally, index_target):
        if l_ally[index_target].round_earth > 0:
            l_ally[index_target].lvl_earth = max(round(self.support * 1.05),l_ally[index_target].lvl_earth)
            l_ally[index_target].round_earth += 1
        else:
            l_ally[index_target].lvl_earth = round(self.support * 1.05)
            l_ally[index_target].round_earth = 2
        for i in range(len(l_ally) - 1):
            if i != index_target:
                if l_ally[i].health > 0:
                    if l_ally[i].round_earth > 0:
                        l_ally[i].lvl_earth = max(round(self.support * 0.5),l_ally[i].lvl_earth)
                    else:
                        l_ally[i].lvl_earth = round(self.support * 0.5)
                        l_ally[i].round_earth = 2
        print("Bouclier de terre réussi\n")

    def water(self, l_ally, index_target):
        self.cooldown_water = 3
        l_ally[index_target].health += round(self.support * 1.2)
        if l_ally[index_target].health > l_ally[index_target].health_max:
            l_ally[index_target].health = l_ally[index_target].health_max
        l_ally[index_target].round_poison = 0
        l_ally[index_target].round_wind = 0
        l_ally[index_target].round_stun = 0
        l_ally[index_target].round_legendary = 0
        l_ally[index_target].sick = False
        for i in range(len(l_ally) - 1):
            if i != index_target:
                if l_ally[i].health > 0:
                    l_ally[i].health += round(self.support * 0.45)
                    if l_ally[i].health > l_ally[i].health_max:
                        l_ally[i].health = l_ally[i].health_max
                    l_ally[i].sick = False
        print("Soin d'eau réussi\n")

    def poison(self, l_enemy, index_target):
        l_enemy[index_target].round_poison = 3
        l_enemy[index_target].lvl_poison = round(self.damage * 0.334)
        print("Attaque de poison réussi\n")

    def metal(self, l_ally, index_target):
        if l_ally[index_target].round_metal > 0:
            l_ally[index_target].lvl_metal = max(round(self.support*0.95),l_ally[index_target].lvl_metal)
            l_ally[index_target].round_metal += 1
        else:
            l_ally[index_target].lvl_metal = round(self.support * 0.95)
            l_ally[index_target].round_metal = 2
        for i in range(len(l_ally) - 1):
            if i != index_target:
                if l_ally[i].health > 0:
                    if l_ally[i].round_metal > 0:
                        l_ally[i].lvl_metal = max(round(self.support * 0.35),l_ally[i].lvl_metal)
                    else:
                        l_ally[i].lvl_metal = round(self.support * 0.35)
                        l_ally[i].round_metal = 2
        print("Bouclier de métal réussi\n")

    def energy(self, l_enemy, index_target):
        if index_target != 1:
            if l_enemy[1].health > 0:
                index_target_effect = 1
            elif index_target == 2:
                index_target_effect = 0
            else:
                index_target_effect = 2
        else:
            if l_enemy[0].health <= l_enemy[2].health:
                if l_enemy[0].health > 0:
                    index_target_effect = 0
                else:
                    index_target_effect = 2
            else:
                if l_enemy[2].health > 0:
                    index_target_effect = 2
                else:
                    index_target_effect = 0
        damage_dealt = round(((self.damage * 0.7) - l_enemy[index_target_effect].is_protect_earth()
                              - l_enemy[index_target_effect].is_protect_shadow() - self.is_affect_wind()) * l_enemy[
                                 index_target_effect].is_affect_legendary())
        if damage_dealt < 0:
            damage_dealt = 0
        l_enemy[index_target_effect].health -= damage_dealt
        l_enemy[index_target_effect].is_protect_metal(self)
        print("Attaque d'énergie réussi\n")

    def void(self, l_enemy, index_target):
        damage_dealt = self.attack(l_enemy, index_target)
        health_steal = round(damage_dealt * 0.55)
        if self.support > self.damage:
            self.health += round(health_steal * 2.1818)
        else:
            self.health += health_steal
        if self.health > self.health_max:
            self.health = self.health_max
        print("Attaque de vide réussi\n")

    def light(self, l_enemy, index_target):
        damage_dealt = round(((self.damage + self.lvl_light * l_enemy[index_target].nb_of_stack_light)
                              - l_enemy[index_target].is_protect_earth()
                              - l_enemy[index_target].is_protect_shadow()
                              - self.is_affect_wind()) * l_enemy[index_target].is_affect_legendary())
        if damage_dealt < 0:
            damage_dealt = 0
        l_enemy[index_target].health -= damage_dealt
        l_enemy[index_target].is_protect_metal(self)
        if l_enemy[index_target].nb_of_stack_light < 4:
            l_enemy[index_target].nb_of_stack_light += 1
        print("Attaque de lumière réussi\n")

    def shadow(self, l_enemy, index_target):
        if self.nb_of_stack_shadow < 10:
            self.nb_of_stack_shadow += 1
        print("Attaque d'Ombre réussi!")

    def legendary(self, l_enemy, index_target):
        l_enemy[index_target].round_legendary = 3
        print("Attaque légendaire réussi!")

    def disease(self, l_enemy, index_target):
        for i in range(len(l_enemy) - 1):
            l_enemy[i].sick = True
            l_enemy[i].lvl_sick += 5
        print("Attaque viral réussi!")

    def love(self, l_enemy, index_target):
        l_enemy[index_target].in_love[0] = 4
        l_enemy[index_target].in_love[1] = self.index