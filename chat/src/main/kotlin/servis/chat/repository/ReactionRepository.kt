package servis.chat.repository

import servis.chat.dto.Reaction
import org.springframework.stereotype.Component

@Component
class ReactionRepository (
    val reactionRepository: MutableList<Reaction>
) {

    fun react(reaction: Boolean, userToId: Int, userFromId: Int) {
        val ReactionExists = reactionRepository.find { it.userFromId == userFromId && it.userToId == userToId }

        if (ReactionExists != null) {
            ReactionExists.reaction = reaction
        } else {
            reactionRepository.add(Reaction(reactionRepository.size, userFromId, userToId, reaction))
        }
    }
}