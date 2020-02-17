import React from 'react'
import { Button, Header, Image, Modal } from 'semantic-ui-react'
import SearchContainer from 'containers/usersetting/SearchContainer'

const ModalForm = (props: any) => (
<Modal trigger={<Button>{props.placeholder}</Button>} centered={false}>
        <Modal.Content image>
            <Modal.Description>
                <Header>필터 설정</Header>
                <SearchContainer></SearchContainer>
            </Modal.Description>
        </Modal.Content>
    </Modal>
)

export default ModalForm
